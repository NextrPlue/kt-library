<template>

    <v-card outlined>
        <v-card-title>
            RequestPublishing
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <String label="ManuscriptTitle" v-model="value.manuscriptTitle" :editMode="editMode"/>
            <String label="ManuscriptContent" v-model="value.manuscriptContent" :editMode="editMode"/>
            <Number label="AuthorId" v-model="value.authorId" :editMode="editMode"/>
            <String label="AuthorName" v-model="value.authorName" :editMode="editMode"/>
            <String label="AuthorIntroduction" v-model="value.authorIntroduction" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="requestPublishing"
            >
                RequestPublishing
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
    name: 'RequestPublishingCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.id = 0;
        this.value.manuscriptTitle = '';
        this.value.manuscriptContent = '';
        this.value.authorId = 0;
        this.value.authorName = '';
        this.value.authorIntroduction = '';
    },
    watch: {
    },
    methods: {
        requestPublishing() {
            this.$emit('requestPublishing', this.value);
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

