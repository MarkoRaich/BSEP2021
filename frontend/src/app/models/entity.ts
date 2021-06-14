export class Entity {

    id: number;
    email : string;
    password : string;
    commonName : string;
    firstName : string;
    lastName : string;
    organization : string;
    organizationUnit : string;
    state : string;
    country: string;
    securityQuestion: string;
    securityAnswer: string;

    constructor( email: string, password: string, commonName: string,
                 firstName: string, lastName: string, organization: string,
                 organizationUnit: string, state: string, country: string, 
                 question: string, answer: string, id? : number){

        this.email = email;
        this.password = password;
        this.commonName = commonName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.state = state;
        this.country = country;
        this.securityQuestion = question;
        this.securityAnswer = answer;
        this.id = id;

        }

}