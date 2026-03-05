export interface Transaction {
    id?:String;
    type: 'income' | 'expence' | 'bank' ;
    ammount: number;
    category: String;
    date:any; //Firebase Timestamp
    note?:String;
    receiptUrl: String;
}
