<template>
    <div>
        <Number
            label="포인트"
            v-model="value.point"
            :editMode="editMode"
        />
        <Date
            label="CreateAt"
            v-model="value.createAt"
            :editMode="editMode"
        />
        <Date
            label="UpdateAt"
            v-model="value.updateAt"
            :editMode="editMode"
        />
        <Number
            label="CustomerId"
            v-model="value.customerId"
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
    name: 'Point',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "points",
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
