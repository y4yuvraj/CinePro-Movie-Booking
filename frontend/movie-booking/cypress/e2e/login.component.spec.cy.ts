describe('Login Page', () => {
    beforeEach(() => {
        cy.visit('http://localhost:4200/login');
    });

    it('should successfully log in with valid credentials', () => {
        cy.get('[data-cy=loginId-input]').type('johndoe2');
        cy.get('[data-cy=password-input]').type('pw2');
        cy.get('[data-cy=login-button]').click({ multiple: true });
        cy.url().should('include', '/user-dashboard');
    });

    it('should show an error message with invalid credentials', () => {
        cy.get('[data-cy=loginId-input]').type('invalidLoginId');
        cy.get('[data-cy=password-input]').type('invalidPassword');
        cy.get('.container > [data-cy="login-button"] > .mat-button-wrapper').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('.mat-simple-snack-bar-content').should('contain', 'Invalid Details! Try again')
        cy.url().should('include', '/login');
        cy.request({
            method: 'POST',
            url: 'http://localhost:8080/authenticate',
            body: {
                loginId: 'invalidLoginId',
                password: 'invalidPassword',
            },
            failOnStatusCode: false,
        }).then((response) => {
            expect(response.status).to.equal(500);
            expect(response.body.errorMessage).to.equal('Authentication error');
        });
    });

    it('should show Passwword is required when field is empty', () => {
        cy.get('[data-cy=loginId-input]').type('user');
        cy.get('.container > [data-cy="login-button"] > .mat-button-wrapper').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('.mat-simple-snack-bar-content').should('contain', 'Password is required !')

    });

    it('should show LoginId is required when field is empty', () => {
        cy.get('[data-cy=password-input]').type('invalidPassword');
        cy.get('.container > [data-cy="login-button"] > .mat-button-wrapper').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('.mat-simple-snack-bar-content').should('contain', 'LoginId is required !')
    });

    it('should show a Dialog when clicked on forgot password', () => {
        cy.get('.ml').click();
        cy.get('#mat-dialog-0').should('be.visible')
    });

    it('should show a Error when clicked on forgot password with no fields or wrong login id', () => {
        cy.get('.ml').click();
        cy.get('app-forgot-password.ng-star-inserted > .container > .mat-focus-indicator').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('.mat-simple-snack-bar-content').should('contain', 'User not found for loginId')
        cy.get('app-forgot-password.ng-star-inserted > .mat-form-field > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('wrongLoginId');
        cy.get('app-forgot-password.ng-star-inserted > .container > .mat-focus-indicator').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('.mat-simple-snack-bar-content').should('contain', 'User not found for loginId')
    });

    it('should return new password when login Id is correct and should login', () => {
        cy.get('.ml').click();
        cy.get('app-forgot-password.ng-star-inserted > .container > .mat-focus-indicator').click();
        cy.get('.mat-snack-bar-container').should('be.visible');
        cy.get('app-forgot-password.ng-star-inserted > .mat-form-field > .mat-form-field-wrapper > .mat-form-field-flex > .mat-form-field-infix').type('johndoe1');
        cy.get('app-forgot-password.ng-star-inserted > .container > .mat-focus-indicator').click();
        cy.get('.text-center.ng-star-inserted').should('be.visible');
        cy.get('.text-center.ng-star-inserted').should('contain','new password');

    });

});

