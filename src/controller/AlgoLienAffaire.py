from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd

casiers = pd.read_csv('../BDD/Personnes.csv')
affaires = pd.read_csv('../BDD/Affaire.csv')

casiers.head(), affaires.head()

casiers.columns = casiers.columns.str.strip()
affaires.columns = affaires.columns.str.strip()

affaire_nom = "Affaire 2"
affaire = affaires[affaires["Nom_Affaire"] == affaire_nom].iloc[0]
description_affaire = affaire["Description"]

personnes_filtrees = casiers[casiers["Description Crime"].notna()].copy()

descriptions_personnes = personnes_filtrees["Description Crime"].tolist()
descriptions = [description_affaire] + descriptions_personnes

vectorizer = TfidfVectorizer()
tfidf_matrix = vectorizer.fit_transform(descriptions)
similarites = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

personnes_filtrees["Similarité"] = similarites
resultats = personnes_filtrees.sort_values(by="Similarité", ascending=False)

print(f"\n🔍 Résultats pour {affaire_nom} :\n")
for i, row in resultats.head(5).iterrows():
    print(f"- {row['Prénom']} {row['Nom']} (Antécédents : {row['antecedents']})")
    print(f"  ➤ Similarité : {row['Similarité']:.2f}")
    print(f"  ➤ Description : {row['Description Crime']}\n")

top_resultats = resultats.head(6)
colonnes_export = ['Prénom', 'Nom', 'antecedents', 'Similarité', 'Description Crime']

nom_fichier = f"resultats_{affaire_nom}.csv"
top_resultats.to_csv(nom_fichier, columns=colonnes_export, index=False, encoding='utf-8')

print(f"✅ Fichier CSV créé : {nom_fichier}")