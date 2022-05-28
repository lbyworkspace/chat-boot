import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push;
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};


const routes = [
    {
      path:"/",
      redirect:'/login',
      name:"Layout",
      component:()=>import('@/Layout'),
      children:[
        {
          path:"register",
          name:"Register",
          component:()=>import('@/views/register')
        },
        {
          path:"login",
          name:"Login",
          component:()=>import('@/views/login')
        },
        {
          path:"main",
          name:"Main",
          component:()=>import('@/views/container')
        }
      ]
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next)=>{
  if(to.name=='Login'||to.name=='Register') next()
  else {
    console.log(store.state.account)
    if(store.state.account) next()
    else router.push('/')
  }
})

export default router
