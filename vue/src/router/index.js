import Vue from 'vue'
import VueRouter from 'vue-router'
import Manager from '../views/Manager.vue'
import User from "@/views/User.vue";
import Home from "@/views/Home.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: Manager,
    redirect:"/home",
    children:[
      {path:'home',name:'Home',component:Home},
      {path:'user',name:'User',component:User},

    ]
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
