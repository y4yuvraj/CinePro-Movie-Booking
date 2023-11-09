export const login = (loginId: string, password: string) => {
  cy.request({
    method: 'POST',
    url: 'http://localhost:8080/authenticate',
    body: {
      loginId,
      password,
    },
  }).then((response) => {
    if (response.status === 200) {
      localStorage.setItem('secret', response.body.token);
      cy.request({
        method: 'GET',
        url: 'http://localhost:8080/currentuser',
        headers: {
          Authorization: `Bearer ${response.body.token}`,
        },
      }).then((currentUserResponse: any) => {
        // cy.log(currentUserResponse);
        const userStr = localStorage.setItem('user', JSON.stringify(currentUserResponse.body));
        const user = currentUserResponse.body;
        const role = user.authorities[0].authority;
        // cy.log(role);
        if (role == 'ROLE_ADMIN') {
          cy.visit('http://localhost:4200/admin-dashboard')
        }
        else {
          cy.visit('http://localhost:4200/user-dashboard')
        }
      });

    } else {
      cy.log('Login failed');
    }
  });
};

declare global {
  namespace Cypress {
    interface Chainable {
      login(username: string, password: string): Chainable<void>;
    }
  }
}