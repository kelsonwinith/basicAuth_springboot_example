<script lang="ts">
	import AuthenPanel from '$lib/components/SignPanel.svelte';
	import { auth } from '$lib/stores/auth';
</script>

<div class="flex min-h-screen items-center justify-center bg-[#f7f6f3]">
	{#if $auth.token}
		<div>User detail would appear here</div>
		<slot />
	{:else}
		<AuthenPanel />
	{/if}
</div>
