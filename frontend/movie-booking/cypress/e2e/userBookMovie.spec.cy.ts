describe('User Booking Movie', () => {
    beforeEach(() => {
        cy.login('johndoe2', 'pw2');
    });
  
    it('should allow a user to book a movie', () => {
      cy.visit('http://localhost:4200/user-dashboard/user-view-movies');
      cy.wait(2000);
      cy.get('.mat-elevation-z8 tbody tr:first-of-type button:contains("Book")').click();
      cy.get('.swal2-popup input[type="number"]').type('2');
      cy.get('.swal2-popup button:contains("Book")').click();
      cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('be.visible')
      cy.get('.mat-snack-bar-container > [aria-hidden="true"]').should('contain','Tickets booked')
      cy.get('[data-cy="logout-button"]').click();
    });
  
    it('should show an error message when booking fails', () => {
      cy.visit('http://localhost:4200/user-dashboard/user-view-movies');
      cy.wait(2000);
      cy.get('.mat-elevation-z8 tbody tr:first-of-type button:contains("Book")').click();
      cy.get('.swal2-popup input[type="number"]').type('10000'); 
      cy.get('.swal2-popup button:contains("Book")').click();
      cy.get('.mat-snack-bar-container').should('be.visible');
      cy.get('[data-cy="logout-button"]').click();
    });
  }); 