<script lang="ts">
	import AuthenPanel from '$lib/components/SignPanel.svelte';
	import UserDetail from '$lib/components/UserDetail.svelte';
	import { auth } from '$lib/stores/auth';
</script>

<div class="flex min-h-screen items-center justify-center bg-[#f7f6f3]">
	{#if $auth.loading}
		<div>Loading...</div>
	{:else if $auth.token}
		<UserDetail />
	{:else}
		<AuthenPanel />
	{/if}
</div>
