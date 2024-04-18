import { request } from "../../utils/articleRequest.js";
export function postArticlePhoto(data) {
    return request({
        url: '/articlePhoto',
        method: 'post',
        data
    });
}