# Swagger

Swagger documentation can be accessed here:

http://localhost:8080/swagger-ui.html#/

## Catalog APIs example

### Be able to find all decks
```bash
curl -X GET 'http://localhost:8080/api/decks' -vvv
```

### Be able to create a deck
```bash
curl -X POST --header "Content-Type: application/json" 'http://localhost:8080/api/decks' -d '{ "title": "a deck" }' -vvv
```

### Be able to add a card to a deck
```bash
curl -X POST --header "Content-Type: application/json" 'http://localhost:8080/api/decks/876afea3-e297-4366-a91f-01355267171d/card' -d '{ "question": "first question ?", "answer": "first answer" }' -vvv
```

### Be able to find a deck by id
```bash
curl -X GET --header "Content-Type: application/json" 'http://localhost:8080/api/decks/876afea3-e297-4366-a91f-01355267171d' -vvv
```
