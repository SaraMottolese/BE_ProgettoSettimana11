# PROGETTO GESTIONE LIBRERIA

## Gestione model
- All'interno di tutti i model ho inserito un campo id, questo è stato fatto in modo da rendere più agevole una 
futura implementazione 
- All'interno del model User abbiamo l'annotazione @JsonIgnore per il campo password perchè non vogliamo esporre la password nei risultati quando viene richiamato il json

## Gestione controller
- All'interno del AuthorController la funzione di add è stata commentata in quanto viene previsto nell'aggiunta del libro. A mio avviso non esiste un autore senza libri. 

## Errore
All'interno del BookController non è possibile inserire le categorie purtroppo non sono riuscita a capire dove si trovasse l'errore e come risolverlo
