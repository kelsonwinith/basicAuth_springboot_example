<script lang="ts">
	import SignIn from '$lib/components/SignIn.svelte';
	import SignUp from '$lib/components/SignUp.svelte';
	import { ButtonGroup, Button } from 'flowbite-svelte';

	let selectedValue: string = 'signIn';

	function handleSelect(value: string) {
		selectedValue = value || 'signIn';
	}
</script>

<div
	class="flex w-full max-w-md flex-col gap-6 rounded-xl border border-[#e3e2df] bg-white p-8 shadow-none"
>
	<ButtonGroup class="flex w-full">
		<Button
			class="flex-1"
			color={selectedValue === 'signIn' ? 'primary' : 'light'}
			onclick={() => handleSelect('signIn')}>Sign In</Button
		>
		<Button
			class="flex-1"
			color={selectedValue === 'signUp' ? 'primary' : 'light'}
			onclick={() => handleSelect('signUp')}>Sign Up</Button
		>
	</ButtonGroup>
	{#if selectedValue === 'signIn'}
		<SignIn />
	{:else if selectedValue === 'signUp'}
		<SignUp />
	{/if}
</div>
