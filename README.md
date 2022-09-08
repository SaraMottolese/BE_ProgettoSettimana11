# PROGETTO GESTIONE LIBRERIA

## Gestione model
- All'interno di tutti i model ho inserito un campo id, questo è stato fatto in modo da rendere più agevole una 
futura implementazione 
- All'interno del model User abbiamo l'annotazione @JsonIgnore per il campo password perchè non vogliamo esporre la password nei risultati quando viene richiamato il json
