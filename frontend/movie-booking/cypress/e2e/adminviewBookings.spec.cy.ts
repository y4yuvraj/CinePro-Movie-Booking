describe('Admin View Bookings Page', () => {
    beforeEach(() => {
        cy.login('admin', '6324');
    });
  
    it('should display the "View Bookings" page', () => {
      cy.visit('http://localhost:4200/admin-dashboard/view-bookings');
      cy.contains('h1', 'View Bookings').should('be.visible');
      cy.get('.mat-elevation-z8').should('be.visible');
    });
  
    it('should display booking details in the table', () => {
      cy.visit('http://localhost:4200/admin-dashboard/view-bookings');
      cy.get('table').should('be.visible');
      cy.get('thead th').should('have.length', 5);
      cy.get('tbody tr').should('have.length.above', 0);
    });
  
  });