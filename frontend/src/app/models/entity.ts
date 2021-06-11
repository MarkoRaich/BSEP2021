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

    constructor( email: string, password: string, commonName: string,
                 firstName: string, lastName: string, organization: string,
                 organizationUnit: string, state: string, country: string, id? : number){

        this.email = email;
        this.password = password;
        this.commonName = commonName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.state = state;
        this.country = country;
        this.id = id;

        }

}