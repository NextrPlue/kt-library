<template>
    <div>
        <String
            label="고객 이름"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="고객email"
            v-model="value.email"
            :editMode="editMode"
        />
        <Boolean
            label="KT 고객 여부"
            v-model="value.isKtUser"
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
        <SubsciptionId
            offline
            label="구독 ID"
            v-model="value.subsciptionId"
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
    name: 'Customer',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "customers",
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
