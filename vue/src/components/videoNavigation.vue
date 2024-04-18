<template>
    <div id="videoNavigation">
        <div id="videoNavigation-head">
            <div class="videoNavigation-head">首页</div>
            <div class="videoNavigation-head">推荐</div>
            <div class="videoNavigation-head">关注</div>
            <div class="videoNavigation-head">朋友</div>
            <div class="videoNavigation-head">我的</div>
        </div>
        <div id="videoNavigation-foot">
            <div class="videoNavigation-foot">美食</div>
            <div class="videoNavigation-foot">音乐</div>
            <div class="videoNavigation-foot">知识</div>
            <div class="videoNavigation-foot">游戏</div>
            <div class="videoNavigation-foot">体育</div>
        </div>
    </div>
</template>
<script>
export default{
    name:'VideoNavigation',
    props:{
    },
    data(){
        return{

        }
    },
    methods:{
    }
}
</script>
<style>
#videoNavigation{
    color: rgb(139, 140, 145);
}
.videoNavigation-head{
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
}
#videoNavigation-head{
    margin-bottom: 50px;
}
.videoNavigation-foot{
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
}
</style>