# 📝 MyNotes – TP2 Développement Mobile (GINF3)

Application Android développée en **Java** dans le cadre du **Travail Pratique 2** du module **Développement Mobile** à l’ENSA Tanger.

L’objectif est de créer une application de gestion de notes personnelles, avec une architecture claire, une navigation fluide, et une interface intuitive.

---

## ✅ Fonctionnalités

- 📋 **Liste des notes** : affichage de toutes les notes avec titre, date et priorité.
- ➕ **Ajout de note** : formulaire complet (titre, description, date, priorité).
- 👁️ **Consultation détaillée** : affichage complet d’une note au clic.
- 📸 **Caméra & Galerie** : activité dédiée pour capturer ou sélectionner des photos, affichées dans une galerie.
- 🔁 **Navigation fluide** : retour automatique à la liste après enregistrement (`finish()`).
- 🎨 **Adaptateur personnalisé** : couleurs distinctes selon la priorité (Rouge = Haute, Bleu = Moyenne, Gris = Basse).

---

## 🧱 Architecture Technique

L’application respecte une architecture simple et lisible :

| Écran | Activité | Rôle |
|------|--------|------|
| **Liste des notes** | `NoteListActivity` | Affiche la liste via `ListView` |
| **Formulaire d’ajout** | `AddNoteActivity` | Collecte les données de la note |
| **Détail d’une note** | `DetailsNoteActivity` | Affiche les 4 attributs complets |
| **Caméra / Galerie** | `CameraActivity` | Capture ou sélectionne des images |

### Modèle de données
- Classe `Note.java` avec 4 attributs :
  - `nom` (String)
  - `description` (String)
  - `date` (String)
  - `priorite` (String : "Basse", "Moyenne", "Haute")

---

## 🛠️ Logique de l’Adaptateur Personnalisé

L’adaptateur `NoteListAdapter` étend `BaseAdapter` et redéfinit la méthode `getView()` pour :

1. Afficher **le titre** (`text1`) et **la date** (`text2`) sur chaque ligne.
2. Appliquer une **couleur différente au titre** selon le niveau de priorité :
   - 🔴 **Haute** → `Color.RED`
   - 🔵 **Moyenne** → `Color.BLUE`
   - ⚪ **Basse** → `Color.GRAY`

Cela permet une **identification visuelle immédiate** de l’importance des notes, comme exigé dans le cahier des charges.

Les données sont passées via un **Intent explicite** avec `putExtra()`, et la navigation utilise `finish()` pour garantir le retour correct à la liste après enregistrement.

---

## 📁 Structure du Projet
app/
└── src/main/
├── java/com/example/mynotes/
│ ├── Note.java
│ ├── NoteListActivity.java
│ ├── AddNoteActivity.java
│ ├── DetailsNoteActivity.java
│ ├── CameraActivity.java
│ └── NoteListAdapter.java (+ PhotoAdapter.java)
└── res/
├── layout/
│ ├── activity_note_list.xml
│ ├── activity_add_note.xml
│ ├── activity_details_note.xml
│ └── activity_camera.xml
└── values/
└── strings.xml


---

## 📸 Captures d’écran

<img width="365" height="613" alt="image" src="https://github.com/user-attachments/assets/77f11011-337e-403b-84d2-e8337c467979" />

*Liste avec couleurs par priorité*

<img width="360" height="620" alt="image" src="https://github.com/user-attachments/assets/d9a76fca-d837-46fb-926c-fd7c1b1d59ea" />

*Formulaire complet avec priorité*

<img width="350" height="817" alt="image" src="https://github.com/user-attachments/assets/b13c97ca-76dd-4d65-a7da-ca0e91642581" />

*Affichage des attributs complets*

<img width="370" height="298" alt="image" src="https://github.com/user-attachments/assets/c5923103-2d26-42aa-9dca-2b77b5a2eafe" />

*Capture photo + galerie*



---

## 🚀 Technologies utilisées

- **Langage** : Java
- **IDE** : Android Studio
- **Cible** : Android API 21+
- **Permissions** : Caméra, stockage externe

---

## 🎓 Réalisé par

**Aymane Arib**  
Étudiant en Génie Informatique – ENSA Tanger  
Module : Développement Mobile – GINF3  
Année universitaire : 2025/2026

---

> ✅ **Conforme au cahier des charges v1.0** – TP2 MyNotes
