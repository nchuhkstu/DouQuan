import { request } from "../utils/articleRequest";
export function publish(data){
    return request({
        url:'/article',
        method:'post',
        data
    })
}