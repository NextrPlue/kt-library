<template>

    <v-card outlined>
        <v-card-title>
            RequestBook
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <PointId offline label="PointId" v-model="value.pointId" :editMode="editMode" @change="change"/>
            <SubsciptionId offline label="SubsciptionId" v-model="value.subsciptionId" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="requestBook"
            >
                RequestBook
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'RequestBookCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.id = 0;
        this.value.pointId = {};
        this.value.subsciptionId = {};
    },
    watch: {
    },
    methods: {
        requestBook() {
            this.$emit('requestBook', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

