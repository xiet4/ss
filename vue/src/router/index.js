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
      {path:'home',name:'主页',component:Home},
      {path:'user',name:'用户管理',component:User},

    ]
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  localStorage.setItem("currentPath",to.name)
  next()
})


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router
