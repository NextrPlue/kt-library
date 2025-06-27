import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/authors',
      component: () => import('../components/ui/AuthorGrid.vue'),
    },
    {
      path: '/customers',
      component: () => import('../components/ui/CustomerGrid.vue'),
    },
    {
      path: '/subsciptions',
      component: () => import('../components/ui/SubsciptionGrid.vue'),
    },
    {
      path: '/manuscripts',
      component: () => import('../components/ui/ManuscriptGrid.vue'),
    },
    {
      path: '/readAuthors',
      component: () => import('../components/ReadAuthorView.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BookGrid.vue'),
    },
    {
      path: '/bookShelves',
      component: () => import('../components/ui/BookShelfGrid.vue'),
    },
  ],
})

export default router;
