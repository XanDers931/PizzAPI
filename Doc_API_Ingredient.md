# API Ingrédient

| URI | Operation | MIME | Requête | Réponse |
|-----|-----------|------|---------|---------|
| /ingredients | GET | <-application/json |  | Liste de tous les ingrédients disponibles (L1)|
| /ingredients/{id} | GET | <-application/json |  | Ingrédient d'id {id} (L1) |
| /ingredients/{id}/name | GET | <-text/plain |  | le nom de l'ingrédient d'id {id} (L2) ou 404 |
| /ingredients/{id}/prix | GET | <-text/plain |  | le prix de l'ingrédient d'id {id} (L3) ou 404 |
| /ingredients | POST | <-/->application/json | Ingrédient (L1) | Nouvel ingrédient  (L1) ou  409 si l'ingrédient existe déjà (même nom) |
| /ingredients/{id} | DELETE |  |  | Delete d'un ingrédient ou 404 | 
| /ingredients/{id} | PATCH | <-/->application/json | Ingrédient(L2 ou L3 ou L4) | Modification des détails d'un ingrédient (L1) ou 404 |

## Corps de Requêtes

### L1
```json
{
  "id":3,
  "name":"sauce",
  "prix":12
}
```

### L2
```json
{
  "name":"sauce",
}
```

### L3
```json
{
  "prix":12
}
```

### L4
```json
{
  "name": "sauce",
  "prix":12
}
```

## Exemples

### Lister tous les ingrédients

```http
GET /ingredients
```

Réponse de la requete du serveur

```json
GET /ingredients

[
    {
        "id":1,
        "name":"mozzarella",
        "prix":3
    },
    {
        "id":2,
        "name":"tomate",
        "prix":2
    }
]
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |

### Les détails d'un ingrédient

```http
GET /ingredients/{id}
```

Réponse de la requete du serveur

```json
GET /ingredients/1

{
    "id":1,
    "name":"mozzarella",
    "prix":3
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'ingrédient n'existe pas |

### Le nom d'un ingrédient

```http
GET /ingredients/{id}/name
```

Réponse de la requete du serveur

```json
GET /ingredients/1/name

{
    "name":"mozzarella",
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'ingrédient n'existe pas |

### Le prix d'un ingrédient

```http
GET /ingredients/{id}/prix
```

Réponse de la requete du serveur

```json
GET /ingredients/1/prix

{
    "prix": 3,
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'ingrédient n'existe pas |

### Ajouter un nouvel ingrédient

```http
POST /ingredients
```

Requête envoyée au serveur

```json
POST /ingredients

{
    "id":1,
    "name":"mozzarella",
    "prix":3
}
```

Réponse de la requete du serveur

```json
{
    "id":1,
    "name":"mozzarella",
    "prix":3
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 409 CONFLICT | Un ingrédient avec le même id existe déjà |

### Supprimer un ingrédient

```http
DELETE /ingredients/{id}
```

Réponse de la requete du serveur

```json
POST /ingredients/1
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'ingrédient n'existe pas |

### Modification des détails d'un ingrédient

```http
PATCH /ingredients/{id}
```

Requête envoyée au serveur

```json
PATCH /ingredients/1

{
    "name": "mozzarella",
} 

ou

{
    "prix": 3,
} 

ou 

{
    "name": "mozzarella",
    "prix": 3,
} 
```

Réponse de la requête du serveur

```json
{
    "id":1,
    "name":"mozzarella",
    "prix":3
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'ingrédient n'existe pas |