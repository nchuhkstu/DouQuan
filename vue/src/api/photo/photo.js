import { request } from "../../utils/photoRequest.js";
export function uploadPhoto(formData) {
    return request({
        url: '/photo/upload',
        method: 'post',
        data: formData,
    });
}