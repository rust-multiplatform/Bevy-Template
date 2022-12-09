use bevy::prelude::*;

pub struct GreetPeoplePlugin;

#[derive(Component)]
struct Person;

#[derive(Component)]
struct Name(String);

#[derive(Resource)]
struct GreetTimer(Timer);

impl Plugin for GreetPeoplePlugin {
    fn build(&self, app: &mut App) {
        app
            // Insert our Timer as a resource
            .insert_resource(GreetTimer(Timer::from_seconds(2.0, TimerMode::Repeating)))
            // Startup Systems run once at startup
            .add_startup_system(add_persons)
            // Normal Systems run every frame
            .add_system(greet_people);
    }
}

fn add_persons(mut commands: Commands) {
    commands.spawn((Person, Name("from Rust".to_string())));
    commands.spawn((Person, Name("Someone".to_string())));
    commands.spawn((Person, Name("SomeFirstName SomeLastName".to_string())));
}

fn greet_people(time: Res<Time>, mut timer: ResMut<GreetTimer>, query: Query<&Name, With<Person>>) {
    // update our timer with the time elapsed since the last update
    // if that caused the timer to finish, we say hello to everyone
    if timer.0.tick(time.delta()).just_finished() {
        for person in query.iter() {
            println!("Hello {}!", person.0);
        }
    }
}
