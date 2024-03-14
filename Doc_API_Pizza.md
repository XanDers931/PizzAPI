# API Pizza

| URI | Operation | MIME | Requête | Réponse |
|-----|-----------|------|---------|---------|
| /pizzas | GET | <-application/json |  | Liste de tous les pizzas disponibles (L7)|
| /pizzas/{id} | GET | <-application/json |  | Pizza d'id {id} (L1) |
| /pizzas/{id}/name | GET | <-text/plain |  | Le nom de la pizza d'id {id} (L2) ou 404 |
| /pizzas/{id}/prixbase | GET | <-text/plain |  | Le prix de base de la pizza d'id {id} (L3) ou 404 |
| /pizzas/{id}/pate | GET | <-text/plain |  | Le type de pate de la pizza d'id {id} (L4) ou 404 |
| /pizzas/{id}/prixfinal | GET | <-text/plain |  | Le prix total de la pizza d'id {id} (L5) ou 404 |
| /pizzas | POST | <-/->application/json | Pizza (L1) | Nouvel Pizza  (L1) ou  409 si la Pizza existe déjà (même nom) |
| /pizzas/{id} | POST | <-/->application/json | Ingredient (L1) | Nouvel ingrédient dans la Pizza {id} (voir L1 de [Documentation API Ingrédient](./Doc_API_Ingredient.md)) ou  409 si la Pizza existe déjà (même id) |
| /pizzas/{id} | DELETE |  |  | Delete d'une Pizza ou 404 | 
| /pizzas/{id_pizza}/{id_ingredient} | DELETE |  |  | Delete d'un Ingrédient pour la pizza {id_pizza} ou 404 | 
| /pizzas/{id} | PATCH | <-/->application/json | Pizza (L6) | Modification des détails d'un Pizza (L1) ou 404 |

## Corps de Requêtes

### L1
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

### L2
```json
"Quatre saisons",
```

### L3
```json
12
```

### L4
```json
"Fine"
```

### L5
```json
17
```

### L6
```json
{
  "name": "Quatre Saisons",
  "prix":12
}
```

### L7
```json
[
  {
    "nom": "Regina",
    "pate": "Fine",
    "id": 2,
    "prixBase": 10,
    "ingredients": [
      {
        "id": 1,
        "prix": 4,
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
]
```

## Exemples

### Lister toutes les Pizzas

```http
GET /pizzas
```

#### Réponse de la requete du serveur

```json
GET /pizzas

[
  {
    "nom": "Regina",
    "pate": "Fine",
    "id": 2,
    "prixBase": 10,
    "ingredients": [
      {
        "id": 1,
        "prix": 4,
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
]
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |

### Les détails d'une Pizza

```http
GET /pizzas/{id}
```

#### Réponse de la requete du serveur

```json
GET /pizzas/1

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

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |

### Le nom d'une Pizza

```http
GET /pizzas/{id}/name
```

#### Réponse de la requete du serveur

```json
GET /pizzas/1/name

"Quatre Saisons"
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |

### La pate d'une Pizza

```http
GET /pizzas/{id}/pate
```

#### Réponse de la requete du serveur

```json
GET /pizzas/1/pate

"Fine"
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |


### Le prix de base d'une Pizza

```http
GET /pizzas/{id}/prixbase
```

#### Réponse de la requete du serveur

```json
GET /pizzas/1/prixbase

12
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |

### Le prix final d'une Pizza

```http
GET /pizzas/{id}/prixfinal
```

#### Réponse de la requete du serveur

```json
GET /pizzas/1/prixfinal

19
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |



### Ajouter une nouvelle Pizza

```http
POST /pizzas
```

#### Requête envoyée au serveur

```json
POST /pizzas

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
| 409 CONFLICT | Un Pizza avec le même id existe déjà |

### Ajouter un nouvel ingrédient à la pizza

```http
POST /pizzas/{id_pizza}
```

#### Requête envoyée au serveur

```json
POST /pizzas/1

{
    "id": 1,
    "prix": 4,
    "name": "Tomates"
}

```
ou
```json
{
    "id": 1,
}
```

#### Réponse de la requete du serveur

```json
{
    {
      "id": 1,
      "prix": 4,
      "name": "Tomates"
    }
}
```
ou
```json
{
    "id": 1,
}
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 409 CONFLICT | Une Pizza avec le même id existe déjà |


### Supprimer une Pizza

```http
DELETE /pizzas/{id}
```

#### Réponse de la requete du serveur

```json
POST /pizzas/1
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |

### Supprimer un ingrédient d'une Pizza

```http
DELETE /pizzas/{id_pizza}/{id_ingredient}
```

#### Réponse de la requete du serveur

```json
POST /pizzas/1/1
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |

### Modification des détails d'un Pizza

```http
PATCH /pizzas/{id}
```

#### Requête envoyée au serveur

```json
PATCH /pizzas/1

{
    "name": "Regina",
} 
```
ou
```json
{
    "prix": "3",
} 
```
ou
```json
{
    "pate": "Epaisse",
} 
```
ou 
```json
{
    "name": "Regina",
    "prix": "3",
    "pate": "Epaisse",
} 
```

#### Réponse de la requête du serveur

```json
{
    "nom": "Regina",
    "pate": "Epaisse",
    "id": 2,
    "prixBase": 3,
    "ingredients": [
      {
        "id": 1,
        "prix": 4,
        "name": "Tomates"
      },
      {
        "id": 2,
        "prix": 3,
        "name": "Mozzarella"
      }
    ]
  },
```

#### Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | La Pizza n'existe pas |