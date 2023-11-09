describe('Admin Add Movie Page', () => {
    beforeEach(() => {
        cy.login('admin', '6324');
    });

    it('should add a movie successfully', () => {
        const theatre = `testuser-${Date.now()}-${Math.floor(Math.random() * 10000)}`;
        cy.visit('http://localhost:4200/admin-dashboard/add-movie');
        cy.get('[formControlName=movieName]').type('Example Movie');
        cy.get('[formControlName=theatreName]').type(theatre);
        cy.get('[formControlName=price]').type('10');
        cy.get('[formControlName=totalTickets]').type('100');
        cy.get('[formControlName=availableTickets]').type('50');
        cy.get('[type=submit]').click();
        cy.get('.swal2-popup').should('be.visible');
        cy.get('.swal2-popup').should('contain','Movie Added')
    });

    // it('should show error when same movie is added in same theatre', () => {
    //     cy.visit('http://localhost:4200/admin-dashboard/add-movie');
    //     cy.get('[formControlName=movieName]').type('Harry Potter');
    //     cy.get('[formControlName=theatreName]').type('Elante');
    //     cy.get('[formControlName=price]').type('10');
    //     cy.get('[formControlName=totalTickets]').type('100');
    //     cy.get('[formControlName=availableTickets]').type('50');
    //     cy.get('[type=submit]').click();
    //     cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('be.visible');
    //     cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('contain', 'already has this movie');
    // });

    it('should show error when form not filled correctly', () => {
        cy.visit('http://localhost:4200/admin-dashboard/add-movie');
        cy.get('[formControlName=movieName]').type('Harry Potter');
        cy.get('[formControlName=price]').type('10');
        cy.get('[formControlName=totalTickets]').type('100');
        cy.get('[formControlName=availableTickets]').type('50');
        cy.get('[type=submit]').click();
        cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('be.visible');
        cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('contain', '');
    });

    it('should show error when fields are left empty', () => {
        cy.get('[type=submit]').click();
        cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('be.visible');
        cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('contain', '');
    });

});