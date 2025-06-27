<template>
    <div>
        <Boolean
            label="유효여부"
            v-model="value.isValid"
            :editMode="editMode"
        />
        <String
            label="구독시작일"
            v-model="value.startDate"
            :editMode="editMode"
        />
        <String
            label="구독종료일"
            v-model="value.endDate"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <Date
            label="UpdatedAt"
            v-model="value.updatedAt"
            :editMode="editMode"
        />
        <CustomerId
            offline
            label="고객 ID"
            v-model="value.customerId"
            :editMode="editMode"
            @change="change"
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
    name: 'Subsciption',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subsciptions",
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
