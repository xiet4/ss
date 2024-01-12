import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import Login from "@/views/Login.vue";
import User from "@/views/User.vue";
import Home from "@/views/Home.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: Manage,
    redirect:"/home",
    children:[
      {path:'home',name:'主页',component:Home},
      {path:'user',name:'用户管理',component:User},

    ]
  },
  {
    path: '/login',
    name: 'login', // 路由名称
    component: Login // 组件对象
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
