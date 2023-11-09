
describe('Navbar Component', () => {
    beforeEach(() => {
      cy.visit('http://localhost:4200/');
    });
  
    it('Click the Register button when not logged in', () => {
      cy.get('[data-cy=register-button]').should('be.visible');
      cy.get('[data-cy=login-button]').should('be.visible');
      cy.get('[data-cy=register-button]').click();
      cy.url().should('include', '/register-user');
    });
  
    it('Click the Login button when not logged in', () => {
      cy.get('[data-cy=register-button]').should('be.visible');
      cy.get('[data-cy=login-button]').should('be.visible');
      cy.get('[data-cy=login-button]').click();
      cy.url(). should('include', '/login');
    });

    it('Click the Logout button when user logged in', () => {
      cy.login('admin', '6324');
      cy.visit('http://localhost:4200/admin-dashboard/add-movie');
      cy.get('[data-cy=logout-button]').should('be.visible');
      cy.get('[data-cy=dashboard-button]').should('be.visible');
      cy.get('[data-cy=logout-button]').click();
      cy.url(). should('equal', 'http://localhost:4200/');
    });
    
  });
  