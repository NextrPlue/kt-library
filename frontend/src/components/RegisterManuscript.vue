<template>

    <v-card outlined>
        <v-card-title>
            RegisterManuscript
        </v-card-title>

        <v-card-text>
            <AuthorId offline label="AuthorId" v-model="value.authorId" :editMode="editMode" @change="change"/>
            <String label="ManuscriptTitle" v-model="value.manuscriptTitle" :editMode="editMode"/>
            <String label="ManuscriptContent" v-model="value.manuscriptContent" :editMode="editMode"/>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="registerManuscript"
            >
                RegisterManuscript
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
    name: 'RegisterManuscriptCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.authorId = {};
        this.value.manuscriptTitle = '';
        this.value.manuscriptContent = '';
        this.value.id = 0;
    },
    watch: {
    },
    methods: {
        registerManuscript() {
            this.$emit('registerManuscript', this.value);
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

