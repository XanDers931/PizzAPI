# API Commande

| URI | Operation | MIME | Requête | Réponse |
|-----|-----------|------|---------|---------|
| /commande | GET | <-application/json |  | Liste de tous les commandes disponibles (L1)|
| /commande/{id} | GET | <-application/json |  | Commande d'id {id} (L2) |
| /commande/{id}/name | GET | <-text/plain |  | Le nom de la Commande d'id {id} (L3) ou 404 |
| /commande/{id}/date | GET | <-text/plain |  | La date de la Commande d'id {id} (L4) ou 404 |
| /commande/{id}/prixfinal | GET | <-text/plain |  | Le prix final de la Commande d'id {id} (L5) ou 404 |
| /commande | POST | <-/->application/json | Commande (L2) | Nouvelle Commande  (L2) ou  409 si la commande existe déjà (même nom) |
| /commande/{id} | POST | <-/->application/json | Pizza (L1) | Nouvelle pizza dans la commande {id} (voir L1 de [Documentation API Pizza](./Doc_API_Pizza.md)) ou  409 si la commande existe déjà (même id) |
| /commande/{id} | DELETE |  |  | Delete d'une commande ou 404 | 
| /commande/{id_commande}/{id_pizza} | DELETE |  |  | Delete d'une Pizza dans la commande ou 404 | 

## Corps de Requêtes

### L1
```json
[
  {
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          }
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  },
  {
    "id": 2,
    "name": "Marie Dubois",
    "date": 1709938800000,
    "commandes": [
      {
        "nom": "Margherita",
        "pate": "Fine",
        "id": 1,
        "prixBase": 8,
        "ingredients": [
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
          {
            "id": 3,
            "prix": 5,
            "name": "Jambon"
          }
        ]
      },
      {
        "nom": "Napolitaine",
        "pate": "Fine",
        "id": 3,
        "prixBase": 9,
        "ingredients": [
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
          {
            "id": 4,
            "prix": 2,
            "name": "Champignons"
          }
        ]
      },
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          }
        ]
      }
    ]
  }]
```

### L2

```json
{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  }
```

### L3

```json
"Jean Dupont"
```

### L4

```json
1710025200000
```

### L5

```json
27
```


## Exemples

### Lister toutes les Commandes

```http
GET /commande
```

#### Réponse de la requete du serveur

```json
GET /commande

[
  {
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          }
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  },
  {
    "id": 2,
    "name": "Marie Dubois",
    "date": 1709938800000,
    "commandes": [
      {
        "nom": "Margherita",
        "pate": "Fine",
        "id": 1,
        "prixBase": 8,
        "ingredients": [
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
          {
            "id": 3,
            "prix": 5,
            "name": "Jambon"
          }
        ]
      },
      {
        "nom": "Napolitaine",
        "pate": "Fine",
        "id": 3,
        "prixBase": 9,
        "ingredients": [
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
          {
            "id": 4,
            "prix": 2,
            "name": "Champignons"
          }
        ]
      },
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          }
        ]
      }
    ]
  }]
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |

### Les détails d'une commande

```http
GET /commande/{id}
```

#### Réponse de la requete du serveur

```json
GET /commande/1

{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  }
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |

### Le nom du destinataire d'une commande

```http
GET /commande/{id}/name
```

#### Réponse de la requete du serveur

```json
GET /commande/1/name

"Jean Dupont"
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |

### La date d'une commande

```http
GET /commande/{id}/date
```

#### Réponse de la requete du serveur

```json
GET /commande/1/date

1710025200000
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |

### Le prix final d'une commande

```http
GET /commande/{id}/prixfinal
```

#### Réponse de la requete du serveur

```json
GET /commande/1/prixfinal

27
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |

### Ajouter une nouvelle commande

```http
POST /commande
```

#### Requête envoyée au serveur

```json
POST /commande

{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  }
```
ou
```json
{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "id": 2
      }
      {
        "id": 4
      }
    ]
  }
```

#### Réponse de la requete du serveur

```json
POST /commande

{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "nom": "Regina",
        "pate": "Epaisse",
        "id": 2,
        "prixBase": 5,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 2,
            "prix": 3,
            "name": "Mozzarella"
          },
        ]
      },
      {
        "nom": "Quatre saisons",
        "pate": "Fine",
        "id": 4,
        "prixBase": 12,
        "ingredients": [
          {
            "id": 1,
            "prix": 2,
            "name": "Tomates"
          },
          {
            "id": 6,
            "prix": 1,
            "name": "Poivron"
          }
        ]
      }
    ]
  }
```
ou
```json
{
    "id": 1,
    "name": "Jean Dupont",
    "date": 1710025200000,
    "commandes": [
      {
        "id": 2
      }
      {
        "id": 4
      }
    ]
  }
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 409 CONFLICT | Un commande avec le même id existe déjà |

### Ajouter une nouvelle pizza à la commande

```http
POST /commande/id
```

#### Requête envoyée au serveur

```json
POST /commande/1

{
  "nom": "Quatre saisons",
  "pate": "Fine",
  "id": 4,
  "prixBase": 12,
  "ingredients": [
    {
      "id": 1,
      "prix": 4,
      "name": "Tomates"
    },
    {
      "id": 6,
      "prix": 1,
      "name": "Poivron"
    }
  ]
}
```
ou
```json
{
  "nom": "Quatre saisons",
  "pate": "Fine",
  "id": 4,
  "prixBase": 12,
  "ingredients": [
    {
      "id": 1,
    },
    {
      "id": 6,
    }
  ]
}
```

#### Réponse de la requete du serveur

```json
{
  "nom": "Quatre saisons",
  "pate": "Fine",
  "id": 4,
  "prixBase": 12,
  "ingredients": [
    {
      "id": 1,
      "prix": 4,
      "name": "Tomates"
    },
    {
      "id": 6,
      "prix": 1,
      "name": "Poivron"
    }
  ]
}
```
ou
```json
{
  "nom": "Quatre saisons",
  "pate": "Fine",
  "id": 4,
  "prixBase": 12,
  "ingredients": [
    {
      "id": 1,
    },
    {
      "id": 6,
    }
  ]
}
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 409 CONFLICT | Une commande avec le même id existe déjà |

### Supprimer une Commande

```http
DELETE /commande/{id}
```

#### Réponse de la requete du serveur

```json
POST /commande/1
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |

### Supprimer une pizza d'une commande

```http
DELETE /commande/{id_commande}/{id_pizza}
```

#### Réponse de la requete du serveur

```json
POST /commande/1/1
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La commande n'existe pas |