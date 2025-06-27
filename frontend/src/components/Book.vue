<template>
    <div>
        <String
            label="요약"
            v-model="value.summary"
            :editMode="editMode"
        />
        <String
            label="커버url"
            v-model="value.coverUrl"
            :editMode="editMode"
        />
        <String
            label="도서url"
            v-model="value.bookUrl"
            :editMode="editMode"
        />
        <Date
            label="생성일"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <Date
            label="수정일"
            v-model="value.updatedAt"
            :editMode="editMode"
        />
        <String
            label="원고제목"
            v-model="value.manuscriptTitle"
            :editMode="editMode"
        />
        <String
            label="원고내용"
            v-model="value.manuscriptContent"
            :editMode="editMode"
        />
        <Number
            label="작가ID"
            v-model="value.authorId"
            :editMode="editMode"
        />
        <String
            label="작가이름"
            v-model="value.authorName"
            :editMode="editMode"
        />
        <String
            label="작가소개"
            v-model="value.introduction"
            :editMode="editMode"
        />
        <String
            label="책분류"
            v-model="value.category"
            :editMode="editMode"
        />
        <Number
            label="책가격"
            v-model="value.price"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Book',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "books",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
