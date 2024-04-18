import { createRouter,createWebHistory} from "vue-router";
const router = createRouter({
    routes:[
        {
            name: '首页',
            component:() => import('../pages/home.vue')
        },
        {
            name: '关于',
            path:'/about',
            component:() => import('../pages/about.vue')
        },
        {
            name:'注册',
            path:'/regist',
            component:() => import('../pages/regist.vue')
        },
        {
            name:'注册成功',
            path:'/registAccess',
            component:() => import('../pages/registAccess.vue')
        },
        {
            name:'视频上传',
            path:'/videoPublish',
            component:() => import('../components/videoPublish.vue')
        },
        {
            name:'测验',
            path:'/test',
            component:() => import('../pages/test.vue')
        },
    ],
    history:createWebHistory()
})

export default router