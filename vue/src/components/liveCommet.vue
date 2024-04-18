<template>
    <div id="liveComment">
        <label id="liveComment-name">{{ name +`：`}}</label>
        <label id="liveComment-content">{{ content }}</label>
    </div>
</template>
<script>
export default{
    name:'liveComment',
    props:{
        name:String,
        content:String,
    }
}
</script>
<style>
#liveComment{
    display: flex;
}
#liveComment-name{
    color: #ff8200;
}
#liveComment-content{
    color: rgb(191, 191, 191);
}
</style>