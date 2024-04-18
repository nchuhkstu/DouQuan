import { request } from "../../utils/articleRequest.js";
export function getComment(articleid,pageSize,currentPage) {
    return request({
        url: `/articleComment/${articleid}/${pageSize}/${currentPage}`,
        method: 'get',
    });
}
export function comment(articleid,comment){
    return request({
        url:`/articleComment/${articleid}`,
        data:comment,
        method:'post',
    })
}
export function deleteComment(commentid){
    return request({
        url:`/articleComment/${commentid}`,
        method:'delete',
    })
}