<template>

    <v-card outlined>
        <v-card-title>
            DisapproveAuthor
        </v-card-title>

        <v-card-text>
            <Boolean label="IsApproved" v-model="value.isApproved" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="disapproveAuthor"
            >
                DisapproveAuthor
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
    name: 'DisapproveAuthorCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.isApproved = false;
    },
    watch: {
    },
    methods: {
        disapproveAuthor() {
            this.$emit('disapproveAuthor', this.value);
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

