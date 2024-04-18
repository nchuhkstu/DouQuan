import { request } from "../utils/request.js";
export function getquestion(data){
    return request({
        url:'/question',
        method:'get',
        data
    })
}