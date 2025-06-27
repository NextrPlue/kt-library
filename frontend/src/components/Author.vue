<template>
    <div>
        <String
            label="작가email"
            v-model="value.email"
            :editMode="editMode"
        />
        <String
            label="작가이름"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="작가소개"
            v-model="value.introduction"
            :editMode="editMode"
        />
        <Boolean
            label="승인여부"
            v-model="value.isApproved"
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
    name: 'Author',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "authors",
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
