<template>

    <v-card outlined>
        <v-card-title>
            RegisterAuthor
        </v-card-title>

        <v-card-text>
            <String label="Email" v-model="value.email" :editMode="editMode"/>
            <String label="Name" v-model="value.name" :editMode="editMode"/>
            <String label="Introduction" v-model="value.introduction" :editMode="editMode"/>
            <Boolean label="IsApproved" v-model="value.isApproved" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="registerAuthor"
            >
                RegisterAuthor
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
    name: 'RegisterAuthorCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.email = '';
        this.value.name = '';
        this.value.introduction = '';
        this.value.isApproved = false;
    },
    watch: {
    },
    methods: {
        registerAuthor() {
            this.$emit('registerAuthor', this.value);
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

