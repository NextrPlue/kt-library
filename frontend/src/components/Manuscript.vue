<template>
    <div>
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
            label="작가 ID"
            v-model="value.authorId"
            :editMode="editMode"
        />
        <String
            label="작가이름"
            v-model="value.authorName"
            :editMode="editMode"
        />
        <String
            label="작가설명"
            v-model="value.authorIntroduction"
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
    name: 'Manuscript',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "manuscripts",
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
