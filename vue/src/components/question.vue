<template>
    <div :id="'question-'+questionid" class="question">
        <div id="question-topic">
            ({{ score }}分) {{ topic }}{{ Answer }}
        </div>
        <div id="question-result">{{ result }}</div>
        <div class="question-option-container">
            <div class="question-option" @click="select('A')" :class="{ 'selected': Answer === 'A' }">A</div><label>{{ optionA }}</label>
        </div>
        <div class="question-option-container">
            <div class="question-option" @click="select('B')" :class="{ 'selected': Answer === 'B' }">B</div><label>{{ optionB }}</label>
        </div>
        <div class="question-option-container">
            <div class="question-option" @click="select('C')" :class="{ 'selected': Answer === 'C' }">C</div><label>{{ optionC }}</label>
        </div>
        <div class="question-option-container">
            <div class="question-option" @click="select('D')" :class="{ 'selected': Answer === 'D' }">D</div><label>{{ optionD }}</label>
        </div>
    </div>
</template>
<script>
export default{
    name:'question',
    data(){
        return{
            Answer:null,
            result:null,
            timer:null,
            submit:null,
        }
    },
    props:{
        questionid:Number,
        score:Number,
        topic:String,
        optionA:String,
        optionB:String,
        optionC:String,
        optionD:String,
        answer:String,
    },
    methods:{
        select(option){
            if(!this.submit){
                this.Answer = option;
            }
        },
        displayResult(){
            if(this.submit==true){
                if(this.Answer==this.answer){
                    this.result='正确';
                }
                else{
                    this.result='错误';
                }
            }
        },
    },
    mounted(){

    }
}
</script>
<style>
.question{
    width: 100%;
    color: white;
    background-color: rgb(35, 35, 35);
    border-radius: 10px;
    padding: 10px 5px;
    margin-top: 10px;
    position: relative;
}
#question-topic{
    margin-left: 5px;
}
#question-result{
    position: absolute;
    top: 10px;
    right: 30px;
    font-size: 30px;
}
.question-option-container{
    display: flex;
    margin-top: 8px;
    margin-left: 5px;
    margin-bottom: 10px;
}
.question-option{
    color: rgb(200,200,200);
    width: 25px;
    height: 25px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    border: 0.5px solid #E1EAF4;
    cursor: pointer;
}
.question-option.selected{
    background-color: rgb(125,125,125);
    color: white;
}
.question-option:hover{
    background-color: rgb(125,125,125);
    color: white;
}
label{
    font-size: 18px;
    margin-left: 5px;
}
</style>