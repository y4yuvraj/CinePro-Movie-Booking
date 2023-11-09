
describe('View Movie Component', () => {
    beforeEach(() => {
        cy.login('admin', '4378');
    });

    it('should display a list of movies', () => {
        cy.visit('http://localhost:4200/admin-dashboard/admin-view-movies');
        cy.get('.mat-elevation-z8').should('be.visible');
        cy.get('.mat-elevation-z8 tbody tr').should('have.length.gte', 2);
        cy.get('.mat-elevation-z8 tbody td').contains('Harry Potter').should('be.visible');
    });

    it('should show status of a movie when "Status" button is clicked', () => {
        cy.visit('http://localhost:4200/admin-dashboard/admin-view-movies');
        cy.get(':nth-child(5) > :nth-child(6) > .mat-focus-indicator').click();
        cy.get('.swal2-popup').should('be.visible');

    });

    it('should allow an admin to delete a movie', () => {
        const theatre = `testuser-${Date.now()}-${Math.floor(Math.random() * 10000)}`;
        cy.visit('http://localhost:4200/admin-dashboard/add-movie');
        cy.get('[formControlName=movieName]').type('Example Movie');
        cy.get('[formControlName=theatreName]').type(theatre);
        cy.get('[formControlName=price]').type('10');
        cy.get('[formControlName=totalTickets]').type('100');
        cy.get('[formControlName=availableTickets]').type('50');
        cy.get('[type=submit]').click();
        cy.get('.swal2-popup').should('be.visible');
        cy.get('.swal2-popup').should('contain', 'Movie Added')
        cy.visit('http://localhost:4200/admin-dashboard/admin-view-movies');
        cy.get(':nth-child(1) > :nth-child(7) > .mat-focus-indicator').click();
        cy.get('.swal2-popup').should('be.visible');
        cy.get('.swal2-popup button:contains("Delete")').click();
    });
});
