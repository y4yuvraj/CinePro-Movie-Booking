describe('Registration Page', () => {
    beforeEach(() => {
      cy.visit('http://localhost:4200/register-user'); 
    });
  
    it('should successfully register with valid data', () => {
        
      const loginId = `testuser-${Date.now()}-${Math.floor(Math.random() * 10000)}`;
      const email = `testuser-${Date.now()}@example.com`;
      cy.get('.mat-form-field.ng-tns-c86-0 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type(loginId);
      cy.get('.mat-form-field.ng-tns-c86-1 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
      cy.get('.mat-form-field.ng-tns-c86-2 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
      cy.get('.mat-form-field.ng-tns-c86-3 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('John');
      cy.get('.mat-form-field.ng-tns-c86-4 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('Doe');
      cy.get('.mat-form-field.ng-tns-c86-5 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type(email);
      cy.get('.mat-form-field.ng-tns-c86-6 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('1234567890');
      cy.get('.container > .mat-primary').click({force: true});
      cy.get('.swal2-popup').should('be.visible')
      cy.get('.swal2-popup').should('contain', 'Success');
      cy.url().should('include', '/register-user'); 
    });
  
    it('should show error when login id already exist', () => { 
        const email = `testuser-${Date.now()}@example.com`;      
        cy.get('.mat-form-field.ng-tns-c86-0 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('johndoe1');
        cy.get('.mat-form-field.ng-tns-c86-1 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-2 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-3 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('John');
        cy.get('.mat-form-field.ng-tns-c86-4 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('Doe');
        cy.get('.mat-form-field.ng-tns-c86-5 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type(email);
        cy.get('.mat-form-field.ng-tns-c86-6 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('1234567890');
        cy.get('.container > .mat-primary').click({force: true});
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('be.visible');
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('contain','LoginId already exists');
        cy.url().should('include', '/register-user'); 
      });

      it('should show error when Email id already exist', () => {   
        const loginId = `testuser-${Date.now()}-${Math.floor(Math.random() * 10000)}`;  
        cy.get('.mat-form-field.ng-tns-c86-0 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type(loginId);
        cy.get('.mat-form-field.ng-tns-c86-1 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-2 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-3 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('John');
        cy.get('.mat-form-field.ng-tns-c86-4 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('Doe');
        cy.get('.mat-form-field.ng-tns-c86-5 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('john1@example.com');
        cy.get('.mat-form-field.ng-tns-c86-6 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('1234567890');
        cy.get('.container > .mat-primary').click({force: true});
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('be.visible');
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('contain','Email already exists.');
        cy.url().should('include', '/register-user'); 
      });


      it('should show error when Invalid phone number', () => {   
        cy.get('.mat-form-field.ng-tns-c86-0 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('testest');
        cy.get('.mat-form-field.ng-tns-c86-1 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-2 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-3 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('John');
        cy.get('.mat-form-field.ng-tns-c86-4 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('Doe');
        cy.get('.mat-form-field.ng-tns-c86-5 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('testest@example.com');
        cy.get('.mat-form-field.ng-tns-c86-6 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('1234');
        cy.get('.container > .mat-primary').click({force: true});
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('be.visible');
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('contain','Please fill out the form correctly!!');
        cy.url().should('include', '/register-user'); 
      });

      it('should show error when password dont match', () => {   
        cy.get('.mat-form-field.ng-tns-c86-0 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('testest');
        cy.get('.mat-form-field.ng-tns-c86-1 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw');
        cy.get('.mat-form-field.ng-tns-c86-2 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('pw2');
        cy.get('.mat-form-field.ng-tns-c86-3 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('John');
        cy.get('.mat-form-field.ng-tns-c86-4 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('Doe');
        cy.get('.mat-form-field.ng-tns-c86-5 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('testest@example.com');
        cy.get('.mat-form-field.ng-tns-c86-6 > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('1234567890');
        cy.get('.container > .mat-primary').click({force: true});
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('be.visible');
        cy.get('#cdk-overlay-1 > .mat-snack-bar-container').should('contain','Passwords do not match!!');
        cy.url().should('include', '/register-user'); 
      });


  });