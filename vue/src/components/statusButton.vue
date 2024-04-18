<template>
    <div :id="'statusButton'+id" class="statusButton">
        <div id="statusButton-circle"></div>
    </div>
</template>
<script>
export default{
    name:'statusButton',
    props:{
        id:Number,
        initStatus:Boolean,
    },
    data(){
        return{
            status:this.initStatus,
            timer:null,
        }
    },
    methods:{
        updateButton(){
            const statusButton = document.getElementById('statusButton'+this.id);
            const statusButtonCircle = statusButton.querySelector('#statusButton-circle');
            if(this.status==true){
                statusButton.style.backgroundColor = 'rgb(180,180,180)';
                statusButtonCircle.style.marginLeft = '2px';
                this.status=false;
            }
            else{
                statusButton.style.backgroundColor = 'rgb(255,44,85)';
                statusButtonCircle.style.marginLeft = '16px';
                this.status=true;
            }
        },
        init(){
            const statusButton = document.getElementById('statusButton'+this.id);
            const statusButtonCircle = statusButton.querySelector('#statusButton-circle');
            if(this.initStatus==true){
                statusButton.style.backgroundColor = 'rgb(255,44,85)';
                statusButtonCircle.style.marginLeft = '16px';
            }
            else{
                statusButton.style.backgroundColor = 'rgb(180,180,180)';
                statusButtonCircle.style.marginLeft = '2px';
            }
        }
    },
    mounted(){
        this.init();
    }
}
</script>
<style>
.statusButton{
    height: 100%;
    width: 100%;
    border-radius: 10px;
    display: flex;
    align-items: center;
    background-color: rgb(180,180,180);
    background-color: rgb(255,44,85);
}
#statusButton-circle{
    height: 16px;
    width: 16px;
    border-radius: 50%;
    background-color: rgb(230,230,230);
    margin-left: 16px;
}
</style>