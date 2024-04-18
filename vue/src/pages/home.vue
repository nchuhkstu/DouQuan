<template>
    <div id="home">
        <div id="home-head">
            <navigation @changeComponents="changeComponents" @goSearch="handleSearch" ></navigation>
        </div>
        <div id="home-body">
            <div v-if="!isSearchMode" style="width: 100%; height: 100%;">
                <component :is="activeComponent" @goZone="goZone" :userid="userid"></component>
            </div>
            <div v-else>
                <search :key="searchKey" :searching="searching" @update-searching="handleUpdateSearching"></search>
            </div>
        </div>
    </div>
</template>
<script>
import navigation from '../components/navigation.vue';
import moments from '../components/moments.vue';
import tiktok from '../components/video.vue';
import live from '../components/live.vue';
import search from '../components/search.vue';
import zone from '../components/zone.vue';
export default{
    components:{
        navigation,
        moments,
        tiktok,
        live,
        search,
        zone,
    },
    data(){
        return{
            activeComponent:'moments',
            searching:'',
            isSearchMode: false,
            searchKey: 0,
            userid:null,
        }
    },
    methods:{
        changeComponents(component){
            if(component){
                this.activeComponent = component;
                this.isSearchMode = false;
            }
        },
        handleSearch(data){
            console.log("123")
                this.searching = data;
                this.isSearchMode = true;
                this.searchKey += 1;
        },
        handleUpdateSearching(data){
            this.searching = data;
            this.searchKey += 1;
        },
        goZone(userid){
            this.activeComponent = 'zone';
            this.userid = userid;
            console.log(userid);
        }
    }
}
</script>
<style>
#home{
    height: 93.4vh;
}
#home-head{
    width: 100%;
    height: 5%;
    min-height: 50px;
    position: fixed;
    z-index: 5;
    top: 0px;
    min-width: 700px;
}
#home-body{
    width: 100%;
    margin-top: 50px;
    height: 100%;
}
</style>