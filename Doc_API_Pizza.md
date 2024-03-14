# API Pizza

| URI | Operation | MIME | Requête | Réponse |
|-----|-----------|------|---------|---------|
| /pizzas | GET | <-application/json |  | Liste de tous les pizzas disponibles (L1)|
| /pizzas/{id} | GET | <-application/json |  | Pizza d'id {id} (L1) |
| /pizzas/{id}/name | GET | <-text/plain |  | le nom de la pizza d'id {id} (L2) ou 404 |
| /pizzas/{id}/prixbase | GET | <-text/plain |  | le prix de base de la pizza d'id {id} (L3) ou 404 |
| /pizzas/{id}/pate | GET | <-text/plain |  | le type de pate de la pizza d'id {id} (L4) ou 404 |
| /pizzas/{id}/prixfinal | GET | <-text/plain |  | le prix total de la pizza d'id {id} (L5) ou 404 |
| /pizzas | POST | <-/->application/json | Pizza (L1) | Nouvel Pizza  (L1) ou  409 si l'Pizza existe déjà (même nom) |
| /pizzas/{id} | DELETE |  |  | Delete d'un Pizza ou 404 | 
| /pizzas/{id} | PATCH | <-/->application/json | Pizza(L2 ou L3 ou L6 | Modification des détails d'un Pizza (L1) ou 404 |

## Corps de Requêtes

### L1
```json
{"nom":"3 fromages",
 "pate":"Fine",
 "id":6,
 "prixBase":10,
 "ingredients":[{"id":9,"prix":4,"name":"Chèvre"},
                {"id":2,"prix":3,"name":"Mozzarella"},
                {"id":13,"prix":3,"name":"Ricotta"}]}
```

### L2
```json
{
  "nom":"3 fromages",
}
```

### L3
```json
{
  "prixbase":12
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

### Lister tous les Pizzas

```http
GET /pizzas
```

Réponse de la requete du serveur

```json
GET /pizzas

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

### Les détails d'un Pizza

```http
GET /pizzas/{id}
```

Réponse de la requete du serveur

```json
GET /pizzas/1

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
| 404 NOT FOUND | L'Pizza n'existe pas |

### Le nom d'un Pizza

```http
GET /pizzas/{id}/name
```

Réponse de la requete du serveur

```json
GET /pizzas/1/name

{
    "name":"mozzarella",
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'Pizza n'existe pas |

### Le prix d'un Pizza

```http
GET /pizzas/{id}/prix
```

Réponse de la requete du serveur

```json
GET /pizzas/1/prix

{
    "prix": 3,
}
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'Pizza n'existe pas |

### Ajouter un nouvel Pizza

```http
POST /pizzas
```

Requête envoyée au serveur

```json
POST /pizzas

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
| 409 CONFLICT | Un Pizza avec le même id existe déjà |

### Supprimer un Pizza

```http
DELETE /pizzas/{id}
```

Réponse de la requete du serveur

```json
POST /pizzas/1
```

Codes de status HTTP

| Status | Description |
|--------|-------------|
| 200 OK | La requête s'est effectuée correctement |
| 404 NOT FOUND | L'Pizza n'existe pas |

### Modification des détails d'un Pizza

```http
PATCH /pizzas/{id}
```

Requête envoyée au serveur

```json
PATCH /pizzas/1

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
| 404 NOT FOUND | L'Pizza n'existe pas |