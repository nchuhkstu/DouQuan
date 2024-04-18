import { request } from "../../utils/commentRequest";
export function getCommentById(commentid){
    return request({
        url:`/comment/${commentid}`,
        method:'get',
    })
}