describe('Home Page Test', () => {
  it('Should display carousel images and captions', () => {

    cy.visit('http://localhost:4200/');
    cy.get('ngb-carousel').should('be.visible').should('exist');
    cy.get('.img-wrapper img')
      .eq(0)
      .should('have.attr', 'src', '../../../assets/harrypotter.jpg');
    cy.get('ngb-carousel .carousel-control-next').click();
    cy.get('.img-wrapper img')
      .eq(1)
      .should('have.attr', 'src', '../../../assets/narnia.jpg');
    cy.get('ngb-carousel .carousel-control-next').click();
    cy.get('.img-wrapper img')
      .eq(2)
      .should('have.attr', 'src', '../../../assets/oppenheimer.jpg');

  });
});
