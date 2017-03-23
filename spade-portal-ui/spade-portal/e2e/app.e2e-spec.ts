import { SpadePortalUiPage } from './app.po';

describe('spade-portal-ui App', function() {
  let page: SpadePortalUiPage;

  beforeEach(() => {
    page = new SpadePortalUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
