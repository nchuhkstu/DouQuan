<template>
    <div id="test">
        <h1>2023前端技术考试模块</h1>
        <div id="question-container">
            <question v-for="question in questions" :ref="'questionRef'+question.questionid" :questionid="question.questionid" :score="question.score" :topic="question.topic" :optionA="question.optionA" :optionB="question.optionB" :optionC="question.optionC" :optionD="question.optionD" :answer="question.answer"></question>
        </div>
        <div id="question-submit-container">
            <button id="question-submit" @click="submit">提交</button>
        </div>
        <div id="test-result">你的测试成绩为：{{ score }}，{{ result }}</div>
    </div>
</template>
<script>
import question from '../components/question.vue';
import {getquestion} from '../api/question';
import {passtest} from '../api/user';
export default{
    data(){
        return{
            questions:[],
            score:0,
            result:null,
        }
    },
    components:{
        question,
    },
    methods:{
        getquestion(){
            getquestion().then(response=>{
                for(let i=0;i<response.data.data.length;i++){
                    this.questions.push({
                        questionid:response.data.data[i].questionid,
                        score:response.data.data[i].score,
                        topic:response.data.data[i].topic,
                        optionA:response.data.data[i].optiona,
                        optionB:response.data.data[i].optionb,
                        optionC:response.data.data[i].optionc,
                        optionD:response.data.data[i].optiond,
                        answer:response.data.data[i].answer,
                    })
                }
            })
        },
        submit(){
            for(let i=0;i<this.questions.length;i++){
                this.$refs['questionRef' + this.questions[i].questionid][0].submit=true;
                this.$refs['questionRef' + this.questions[i].questionid][0].displayResult();
                if(this.$refs['questionRef' + this.questions[i].questionid][0].result=='正确'){
                    this.score = this.score + this.$refs['questionRef' + this.questions[i].questionid][0].score;
                }
            }
            if(this.score>=30){
                passtest().then(response=>{
                    this.result = '恭喜你通过测试，成为网站正式成员';
                })
            }
            else{
                this.result = '很遗憾，您未通过测试';
            }
            document.getElementById("test-result").style.display = 'flex';
            document.getElementById("question-submit-container").style.display = 'none';
        }
    },
    mounted(){
        this.getquestion();
    }
}
</script>
<style>
#test{
    background-color: rgb(17, 17, 17);
}
h1{
    color: white;
    text-align: center;
}
#question-container{
    width: 600px;
    margin: 0 auto;
}
#question-submit-container{
    display: flex;
    justify-content: center;
}
#question-submit{
    width: 100px;
    height: 40px;
    border-radius: 10px;
    cursor: pointer;
}
#question-submit:hover{
    background-color: rgb(200,200,200);
}
#test-result{
    display: none;
    justify-content: center;
    color: white;
    font-size: 25px;
    margin-top: 20px;
    padding-bottom: 50px;
}
</style>