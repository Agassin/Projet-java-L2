import sys
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

# Affichage UTF-8 pour la console Windows
sys.stdout.reconfigure(encoding='utf-8')

# Vérification des arguments
if len(sys.argv) < 2:
    print("Erreur : Aucun nom d'affaire reçu.")
    sys.exit(1)

affaire_nom = sys.argv[1]

# Chargement des données
casiers = pd.read_csv('../BDD/Personnes.csv')
affaires = pd.read_csv('../BDD/Affaire.csv')

# Nettoyage des colonnes
casiers.columns = casiers.columns.str.strip()
affaires.columns = affaires.columns.str.strip()

# Filtrer l'affaire
affaire_match = affaires[affaires["Nom_Affaire"] == affaire_nom]

if affaire_match.empty:
    print(f"Aucune affaire trouvée pour le nom : {affaire_nom}")
    sys.exit(1)

affaire = affaire_match.iloc[0]
description_affaire = affaire["Description"]

# Filtrer les personnes avec description
personnes_filtrees = casiers[casiers["Description Crime"].notna()].copy()
descriptions_personnes = personnes_filtrees["Description Crime"].tolist()
descriptions = [description_affaire] + descriptions_personnes

# TF-IDF + similarité cosinus
vectorizer = TfidfVectorizer()
tfidf_matrix = vectorizer.fit_transform(descriptions)
similarites = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

# Ajout des similarités
personnes_filtrees["Similarite"] = similarites
resultats = personnes_filtrees.sort_values(by="Similarite", ascending=False)

# Affichage
print(f"\n Résultats pour {affaire_nom} :\n")
for i, row in resultats.head(3).iterrows():
    print(f"- {row['Prénom']} {row['Nom']} (Antecedents : {row['antecedents']})")
    print(f"  --> Similarite : {row['Similarite']:.2f}")
    print(f"  --> Description : {row['Description Crime']}\n")

# Export CSV
top_resultats = resultats.head(3)
colonnes_export = ['Prénom', 'Nom', 'antecedents', 'Similarite', 'Description Crime']
nom_fichier = f"resultats_affaire.csv"
top_resultats.to_csv(nom_fichier, columns=colonnes_export, index=False, encoding='utf-8')

print(f" Fichier CSV créé : {nom_fichier}")